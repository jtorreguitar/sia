function deltas = deltas_for_each_layer(dactivation_function, weight_output, final_layer_deltas, weights)
    deltas = {final_layer_deltas};
    previous_deltas = final_layer_deltas;
    for i = length(weight_output):-1:1
        aux1=weight_output{i}
        aux2=weights{i}
        aux3 = dactivation_function(weight_output{i})*(previous_deltas*weights{i})
        deltas = [aux3, deltas];
        previous_deltas = deltas(1){1};
    end
end
        
