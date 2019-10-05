function final_layer_deltas = deltas_for_final_layer(dactivation_function, weight_output, activations, expected_output)
    final_layer_weight_outputs = weight_output{length(weight_output)};
    final_layer_activations = activations{length(activations)};
    final_layer_deltas = dactivation_function(final_layer_weight_outputs)*(expected_output .- final_layer_activations);
end
