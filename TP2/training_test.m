neurons = [2 16 16 1];
weights = initialize_weights(neurons);
training_function = @(x,y)(cos(x)*cos(y));
training_set = [];
for i = 1:15
    for j = i:15
        training_set = [training_set; i j training_function(i,j)];
    end
end

error = [];
for i = training_set'
    weight_deltas_and_activations = find_weight_deltas_and_activations(@sigmoid, @sigmoid_derivative, weights, i(1:2), i(3));
    error = [error network_error(weight_deltas_and_activations{2}{1}, i(3))];
    weight_changes = find_weight_changes(weight_deltas_and_activations{1}, [i(1:2) weight_deltas_and_activations{2}]);
    for j = 1:length(weights)
        weights{j} = weights{j} + weight_changes{j};
    end
end

error_after_training = [];
for i = 20:30
    for j = i:30
        expected_result = training_function(i, j);
        result = feed_forward(@sigmoid, [i,j], weights);
        error_after_training = [error network_error(result, expected_result)];
    end
end
