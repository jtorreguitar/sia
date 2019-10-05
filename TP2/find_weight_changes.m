function weight_changes = find_weight_changes(deltas, activations)
    weight_changes = {};
    for i = 1:length(deltas)
        weight_changes = [weight_changes deltas(i){1}*activations(i){1}'];
    end
end
