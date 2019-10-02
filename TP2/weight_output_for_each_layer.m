# calculates the sum of the weights multiplied by the activations of the previous layer for each layer.

function weight_output = weight_output_for_each_layer(weights, initial_activations)
    transposed_activations = initial_activations';
    weight_output = {initial_activations};
    for w = weights
        transposed_activations = w{1}*transposed_activations;
        weight_output = {weight_output, transposed_activations};
    end
end
