wl1 = [0.1, 0.3; 0.2, 0.1];
wl2 = [0.1, 0.2; 0.3, 0.2; 0.4, 0.5];
wl3 = [0.05, 0.15, 0.1];
input_activations = [1;1];
weights = {wl1, wl2, wl3};
weight_output = weight_output_for_each_layer(weights, input_activations);
activations = activations_for_each_layer(@sigmoid, weight_output);
final_layer_deltas = deltas_for_final_layer(@sigmoid_derivative, weight_output, activations, 0);
deltas = deltas_for_each_layer(@sigmoid_derivative, weight_output, final_layer_deltas, weights);
weight_changes = find_weight_changes(deltas, [input_activations activations]);

