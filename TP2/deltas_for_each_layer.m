function deltas = deltas_for_each_layer(dactivation_function, weight_output, final_layer_deltas)
    deltas = {final_layer_deltas};
    for i = fliplr(length(weight_output)-1:1)
        deltas = [deltas, dactivation_function(weight_output{i}).*()
        
