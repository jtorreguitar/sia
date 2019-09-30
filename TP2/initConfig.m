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
neurons = parseParam('neurons');
neurons_size = size(neurons);
layers = neurons_size(2);
epochs = parseParam('epochs');
eta = parseParam('eta');
epsilon = parseParam('epsilon_error');
error_treshold_value = parseParam('error_treshold_value');
error_treshold_flag = parseParam('error_treshold_flag');
shuffle_flag = parseParam('shuffle_flag');
adaptative_eta_flag = parseParam('adaptative_eta_flag');
eta_check_steps = parseParam('eta_check_steps');
eta_increase_value = parseParam('eta_increase_value');
eta_decrease_factor = parseParam('eta_decrease_factor');
momentum_flag = parseParam('momentum_flag');
alpha_momentum_init = parseParam('alpha_momentum');
alpha_momentum = alpha_momentum_init;
pause_gamma = 0.00000001;


if parseParam('tanh')
    activation_function = @tanh;
    activation_function_derivate = @(x)(1 - x.^2);
else
    activation_function = @(x)(1./(1 + exp(-x)));
    activation_function_derivate = @(x)(x.*(1-x));
end

current_seed = rand('seed');
        
terrainSize = size(y, 1);
trainingSize = parseParam('training_size');
testingSize = terrainSize - trainingSize;

%training and testing domains
training_input_domain = [-1*ones(trainingSize, 1) x1(1:trainingSize) x2(1:trainingSize)]';
testing_input_domain = [-1*ones(testingSize, 1) x1((trainingSize+1):terrainSize) x2((trainingSize+1):terrainSize)]';
expected_output = y';

disp(training_input_domain);
disp(testing_input_domain);
disp(expected_output);