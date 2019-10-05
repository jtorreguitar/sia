function weight_deltas_and_activations = find_weight_deltas_and_activations(activation_function, dactivation_function, weights, initial_activations, expected_output)
    weight_output = weight_output_for_each_layer(weights, initial_activations);
    activations = activations_for_each_layer(activation_function, weight_output);
    final_layer_deltas = deltas_for_final_layer(dactivation_function, weight_output, activations, expected_output);
    weight_deltas = deltas_for_each_layer(dactivation_function, weight_output, final_layer_deltas, weights);
    weight_deltas_and_activations = {weight_deltas, activations};
end
