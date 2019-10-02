# activation_function: the function through which the sum of the weights times the activations of the previous layer
#                      will go through.
# initial_activations: the initial activation values.
# weights: a cell matrix containing the weights for the entire network. Each element of the array is a matrix containing
#          the weights from layer i - 1 to layer i.
# return_value (final_activations): the output of the neural network with the given weights and initial_activations.

function final_activations = feed_forward(activation_function, initial_activations, weights)
    transposed_activations = initial_activations';
    for w = weights
        transposed_activations = activation_function(w{1}*transposed_activations);
    end
    final_activations = transposed_activations';
end

