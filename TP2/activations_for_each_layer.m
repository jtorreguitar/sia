# puts the output of each layer through the activation function

function activations = activations_for_each_layer(activation_function, weight_output)
    activations = {};
    for w = weight_output
        activations = [activations, activation_function(w{1})];
    end
end
