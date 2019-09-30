function param = parseParam(paramName)
    pathName = 'config.data';
    neurons_separator = ',';
    NEURONS_CASE = 'neurons';
    INPUT_FILE_CASE = 'input_file';
    SEED_FILE_CASE = 'seed_id';
    
    fileID = fopen(pathName);
    allParams = textscan(fileID,'%s %s');
    fclose(fileID);
    headers = allParams{1};
    values = allParams{2};

    allParams_size = size(headers);
   
    for i = 1:allParams_size(1)
       if (strcmp(headers(i), paramName))    
           if (strcmp(paramName, NEURONS_CASE))
               neurons = values{i};
               splitted = strsplit(neurons, neurons_separator);
               splitted_size = size(splitted);
               aux = zeros(1, splitted_size(2));
               for j=1:splitted_size(2)
                   aux(j) = str2num(splitted{j});
               end
               param = aux;
           elseif (strcmp(paramName, INPUT_FILE_CASE) || strcmp(paramName, SEED_FILE_CASE))
                   param = values{i};
           else
                   param = str2num(values{i});
           end
           return;
       end
    end
end



