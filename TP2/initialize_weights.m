function weights = initialize_weights(neurons)
    weights = {};
    for i = 1:length(neurons)-1
        weights = [weights rand(neurons(i+1), neurons(i))];
    end
end 
