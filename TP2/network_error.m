function error = network_error(network_output, expected_output)
    error = mean((expected_output - network_output).^2);
end
