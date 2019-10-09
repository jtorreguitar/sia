debug_on_interrupt(1);

% parsear archivo de entrada
X1_POS = 1;
X2_POS = 2;
Y_POS = 3;

input_file = parseParam('input_file');
file_data = importdata(input_file, ' ');

x1 = file_data.data(:, X1_POS);
x2 = file_data.data(:, X2_POS);
y = file_data.data(:, Y_POS);

%parsear parametros
% neurons es un array que cada elemento representa la cantidad de cells
% en la layer correspondiente a esa posicion del array
neurons = parseParam('neurons');
neurons_size = size(neurons);
layers = neurons_size(2);
epochs = parseParam('epochs');
eta = parseParam('eta');
epsilon = parseParam('epsilon_error');
error_threshold_value = parseParam('error_threshold_value');
error_threshold_flag = parseParam('error_threshold_flag');
adaptative_eta_flag = parseParam('adaptative_eta_flag');
eta_check_steps = parseParam('eta_check_steps');
eta_increase_value = parseParam('eta_increase_value');
eta_decrease_factor = parseParam('eta_decrease_factor');
momentum_flag = parseParam('momentum_flag');
alpha_momentum_init = parseParam('alpha_momentum');
alpha_momentum = alpha_momentum_init;
saturation_prevention = parseParam('saturation_prevention');
batch = parseParam('batch');
plotting_pause = 0.00000001;


if parseParam('tanh')
    activation_function = @tanh;
    activation_function_derivate = @(x)(1 - x.^2);
else
    activation_function = @(x)(1./(1 + exp(-x)));
    activation_function_derivate = @(x)(x.*(1-x));
end

%podriamos guardar seeds y permitir cargarlas
current_seed = rand('seed');
        
terrainSize = size(y, 1);
if(parseParam('training_percentage_flag'))
    trainingSize = floor(parseParam('training_percentage') * terrainSize);
else
    trainingSize= parseParam('training_size');
end
testingSize = terrainSize - trainingSize;
%armamos las matrices para testing y entrenamiento
training_input_domain = [-1*ones(trainingSize, 1) x1(1:trainingSize) x2(1:trainingSize)]';
testing_input_domain = [-1*ones(testingSize, 1) x1((trainingSize+1):terrainSize) x2((trainingSize+1):terrainSize)]';
expected_output = y';

%cells genericas
% los cells son arreglos de matrices
% en este caso tenemos una matriz x cada layer
weights_cell = cell(layers - 1, 1);
previous_weights_variation = cell(layers - 1, 1);
weighted_sum = cell(layers - 1, 1);
training_delta = cell(layers - 1, 1);
testing_weighted_sum = cell(layers - 1, 1);
batch_layer_sum = cell(layers -1, 1);

%errores
testing_cuadratic_error = 0;
training_cuadratic_error = 0;
training_cuadratic_old_error = 0;

%inicializar cells genericas
for k = 1:(layers-1)
    weights_cell{k} = rand(neurons(k+1), neurons(k)+1) ;
    batch_layer_sum{k} = zeros(neurons(k+1), neurons(k) +1);
    previous_weights_variation{k} = zeros(neurons(k+1), neurons(k)+1);
    training_delta{k} = zeros(neurons(k+1), 1);
    
    if (k ~= layers-1)
        weighted_sum{k} = [-1*ones(trainingSize,1) zeros(trainingSize, neurons(k+1))]';
        testing_weighted_sum{k} = [-1*ones(testingSize,1) zeros(testingSize, neurons(k+1))]';
    else
        weighted_sum{k} = zeros(neurons(k+1), trainingSize);
        testing_weighted_sum{k} = zeros(neurons(k+1), testingSize);
    end
end

% para eta adaptativo
weights_old_cell = weights_cell;

%aca habria que inicializar la visualizacion

cycles = 1;

%plotting
epochs_array= zeros(cycles, 1);
ecm_array = zeros(cycles,1);


%para ciclar
for p = 1:cycles
    printf("EPOCHS: %d\n", epochs);
    eta_change = 0;
    training_error_array = [];
    testing_error_array = [];
    eta_array = [];
    for i = 1:epochs
        eta_array = [eta_array eta];
        %%% TRAINING %%%
        for j = 1:trainingSize
            % TODO : Ver como implementar shuffling

            r=j;

            % FEED FORWARD
            % Llenamos la matriz con los parametros de entrenamiento
            forward_previous = training_input_domain;
            for k = 1:(layers - 1)
                %va creciendo para tener en cuenta las layers nuevas a la hora de hacer la suma
                new_layer = neurons(k+1) + 1;

                if k == layers - 1
                    weighted_sum{k}(r) = activation_function(weights_cell{k} * forward_previous(:, r));
                else
                    ah = weighted_sum{k}(2:new_layer, r) = activation_function(weights_cell{k} * forward_previous(:, r));
                end
                %guardamos la matriz de suma pesada en cada nodo para el proximo layer
                forward_previous = weighted_sum{k};
            end
            

            %BACK PROPAGATION
            for k = (layers - 1):-1:1
                % con {k}(r) accedemos al r set de entrenamiento de la layer k
                % calculo los errores
                new_layer = neurons(k+1) + 1;

                if k == layers - 1
                    training_delta{k} = (activation_function_derivate(weighted_sum{k}(r)) + saturation_prevention).*(expected_output(r) - weighted_sum{k}(r));
                else
                    training_delta{k} = (activation_function_derivate(weighted_sum{k}(2:new_layer, r)) + saturation_prevention).*(weights_cell{k+1}(:, 2:new_layer)' * training_delta{k+1});
                end

                %guardo los pesos anteriores
                if k == 1
                    backward_previous = training_input_domain(:, r)';
                else
                    backward_previous = weighted_sum{k-1}(:,r)';
                end

                %calculo la variacion y la guardo
                
                weight_variation = eta * training_delta{k} * backward_previous;
                %voy sumando las variaciones en una matriz de layer
            
                batch_layer_sum{k} = batch_layer_sum{k} + weight_variation + momentum_flag * alpha_momentum * previous_weights_variation{k};
                previous_weights_variation{k} = weight_variation;
                %si es la iteracion que me toca hacer el cambio
                if( rem(i, batch) == 0 )
                    %calculo el promedio
                    avg_variation =  batch_layer_sum{k}./batch;
                    %afecto los pesos
                    weights_cell{k} = weights_cell{k} + avg_variation + momentum_flag * alpha_momentum * previous_weights_variation{k};
                    previous_weights_variation{k} = avg_variation;
                    %reinicion la suma
                    batch_layer_sum{k} = zeros(neurons(k+1), neurons(k) +1);
                end
                %calculo y guardo los pesos nuevos
                %weight_variation = eta * training_delta{k} * backward_previous;
                %weights_cell{k} = weights_cell{k} + weight_variation + momentum_flag * alpha_momentum * previous_weights_variation{k};
                %previous_weights_variation{k} = weight_variation;
                
            end
            
        end
        
        %calculo los errores de entrenamientoexit
        training_cuadratic_error_prev = training_cuadratic_error;
        training_cuadratic_error = 0.5 * sum((expected_output(1:trainingSize) - weighted_sum{layers-1}).^2)/trainingSize;
        training_abs_error = abs((expected_output(1:trainingSize) - weighted_sum{layers-1}));
        training_error_array = [training_error_array training_cuadratic_error];
        
        %calculo la precision
        counter = 0;
        for k = 1:trainingSize
            if (training_abs_error(k) < epsilon)
                counter = counter + 1;
            end
        end
        training_success_rate = (counter/trainingSize) * 100.0;
        
        %%% TESTING %%%
        testing_forward_previous = testing_input_domain;    
        for k = 1:(layers - 1)
            new_layer = neurons(k+1) + 1;
            if k == layers - 1
                testing_weighted_sum{k} = activation_function(weights_cell{k} * testing_forward_previous);
            else
                testing_weighted_sum{k}(2:new_layer, :) = activation_function(weights_cell{k} * testing_forward_previous);
            end
            testing_forward_previous = testing_weighted_sum{k};
        end
            
        %calculo errores de testing    
        testing_cuadratic_error_prev = testing_cuadratic_error;
        testing_cuadratic_error = 0.5*sum((expected_output((trainingSize+1):terrainSize) - testing_weighted_sum{layers-1}).^2)/(testingSize);
        testing_abs_error = abs((expected_output((trainingSize+1):terrainSize) - testing_weighted_sum{layers-1}));
        testing_error_array = [testing_error_array testing_cuadratic_error];
        %calculo la precision
        counter = 0;
        for k = 1:testingSize
            if (testing_abs_error(k) < epsilon)
                counter = counter + 1;
            end
        end
        testing_success_rate = (counter/testingSize) * 100.0;
           
        if i==1
            training_cuadratic_error_prev = training_cuadratic_error;
        end

        if(adaptative_eta_flag)
             if(training_cuadratic_error < training_cuadratic_old_error)
                eta_change++;
             end
            % si el error actual es mayor que el anterior, entonces nos quedamos con el peso viejo
            % nuestro nuevo eta desciende en BETA
            % cortamos el momentum
            if(training_cuadratic_error > training_cuadratic_error_prev)
                eta_change = 0;
                weights_cell = weights_old_cell;
                eta = eta - eta*eta_decrease_factor;
                alpha_momentum = 0;
            end
            % si el error actual es menor que el anterior, venimos bien
            % entonces incrementamos el eta y guardamos estos pesos
            % y volvemos a poner nuestro momentum inicial
              
            if(rem(eta_change, eta_check_steps) == 0 && (training_cuadratic_error < training_cuadratic_old_error))
               eta_change = 0; 
               eta = eta + eta_increase_value;
               weights_old_cell = weights_cell;
               alpha_momentum = alpha_momentum_init;
            end
            %guardamos el error actual
            training_cuadratic_old_error = training_cuadratic_error;
        end

         
        if (i == 1)
            testing_cuadratic_error_prev = testing_cuadratic_error;
            training_cuadratic_error_best = training_cuadratic_error;
            testing_cuadratic_error_best = testing_cuadratic_error;
        end
        
        %guardamos los mejores resultados
        %calculo que lo vamos a querer imprimir
        if (testing_cuadratic_error < testing_cuadratic_error_best)
            weights_cell_best = weights_cell;
            testing_cuadratic_error_best = testing_cuadratic_error;
        end
        
        % para cortar antes si hay una cota de error
        if (error_threshold_flag)
            if (training_cuadratic_error <= error_threshold_value)
                break
            end
        end

    end
    %print resultados
    printf("Training success rate: %i%%.\n", training_success_rate);
    printf("Testing success rate: %i%%.\n", testing_success_rate);

    epochs_array(p) = epochs;
    ecm_array(p) = testing_cuadratic_error;

end

%plot
plot(1:epochs, training_error_array, 'r-', 1:epochs, testing_error_array, 'b-');
xlabel('epochs');
ylabel('error');
axis([0 epochs 0 0.03]);
legend('training error', 'testing error');
