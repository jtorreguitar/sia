# calculates the sum of the weights multiplied by the activations of the previous layer for each layer.

function weight_output = weight_output_for_each_layer(weights, initial_activations)
    current_activations = initial_activations;
    weight_output = {};
    for w = weights
        current_activations = w{1}*current_activations;
        weight_output = [weight_output, current_activations];
    end
end
