function percep_multicapa()
 psiMatrix = [1 0 1 0; 0 0 1 1]
  S = [0; 1; 1; 0];
  #input_file = parseParam('input_file');
  #file_data = importdata(input_file, ' ');
  #X1_POS = 1;
  #X2_POS = 2;
  #Y_POS = 3;
  #x1 = file_data.data(:, X1_POS)';
  #x2 = file_data.data(:, X2_POS)';
  #y = file_data.data(:, Y_POS);
  #psiMatrix = [x1; x2];
  #S = y;
  SBackup = S;
  psiBackup = psiMatrix;
  n0 = 2;
  n1 = 3;
  nS = 1;
  W0 = rand([n1, (n0 + 1)]);
  W1 = rand([nS, (n1 + 1)]);
  notVisited = rows(S);
  eta = 0.05;
  globalError = 1;
  if rows(S) != columns(psiMatrix)
   return
  endif
  
  while globalError > 0.001
     psi = floor(rand()*notVisited) + 1;
    --notVisited;
      V1 = [calculateOutput(psiMatrix, psi, W0, 1); 
            calculateOutput(psiMatrix, psi, W0, 2);
            calculateOutput(psiMatrix, psi, W0, 3)]
      V2 = calculateOutput(V1, 1, W1, 1)
      deltaS = 2*V2.*(1 - V2).*(S(psi) - V2)
      delta1 = 2*V1.*(1 - V1).*(W1'(2:4,:) * deltaS)
      W1 = W1 + (eta*deltaS*[-1, V1']);
      W0 = W0 + (eta*delta1*[-1, psiMatrix(:,psi)']);
      psiMatrix(:,psi) = [];
      S(psi,:) = [];
      if notVisited == 0 
        psiMatrix = psiBackup;
        S = SBackup;
        notVisited = rows(S);
        O = calculateFinalOutput(psiMatrix, W0, W1);
        globalError = 1/(2*rows(S)) * sum((S - O).^2)
        O
      endif
  endwhile
endfunction

          
function x = calculateOutput(inputMatrix, column, W, row) 
  h = W(row,:)*[-1; inputMatrix(:,column)];
  x = 1/(1 + exp(-2*h));
endfunction

function x = calculateFinalOutput(inputMatrix, W0, W1)
  i = 1;
  x = zeros(columns(inputMatrix), 1);
  while i <= columns(inputMatrix)
    V1 = [calculateOutput(inputMatrix, i, W0, 1); 
          calculateOutput(inputMatrix, i, W0, 2);
          calculateOutput(inputMatrix, i, W0, 3);];
    V2 = calculateOutput(V1, 1, W1, 1);
    x(i) = V2;
    i++;
  endwhile
endfunction

    
    
    
    