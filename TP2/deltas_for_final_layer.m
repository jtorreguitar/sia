function final_layer_deltas = deltas_for_final_layer(dactivation_function, weight_output, activations, expected_output)
    final_layer_deltas = dactivation_function(weight_output{length(weight_output)}.*(expected_output-activations(length(activations)};
end
