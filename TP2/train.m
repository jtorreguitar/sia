# takes in one set of weights, an input and its expected outputs and returns the weights corrected through
# backpropagation

function new_weights = train(activation_function, dactivation_function, weights, initial_activations, expected_output)
    weight_output = weight_output_for_each_layer(weights, initial_activations)
    activations = activations_for_each_layer(activation_function, weight_output)
    final_layer_deltas = deltas_for_final_layer(dactivation_function, weight_output, activations, expected_output)
    deltas = deltas_for_each_layer(dactivation_function, weight_output, final_layer_deltas)
    new_weights = subtract_deltas(weights, deltas)
end
