function percep_simple(sizeW, eta, psi, globalError, W, deltaError, S)
  notVisited = rows(S);
  psiBackup = psi;
  sBackup = S;
  if rows(S) != rows(psi)
    return
  endif
  
  while globalError > deltaError
    row = floor(rand()*notVisited) + 1;
    --notVisited;
    Werror = 2*eta*(S(row)-calculateOh(psi, row, sizeW, W))*psi(row,1:sizeW)'
    W = W + Werror;
    psi(row,:) = [];
    S(row,:) = [];
    if notVisited == 0 
      notVisited = 4;
      psi = psiBackup;
      S = sBackup;
      O = [calculateOh(psi, 1, sizeW, W), 
           calculateOh(psi, 2, sizeW, W), 
           calculateOh(psi, 3, sizeW, W), 
           calculateOh(psi, 4, sizeW, W)];
      #8 es 2 x 4, donde 4 es la cantida de mu's
      globalError = 1/8 * sum((S - O).^2)
     endif 
  endwhile
  
endfunction  


#  sizeW = 3;
#  eta = 0.01;
#  W = rand([sizeW,1])-0.5;
#  psi = [-1 1 1; -1 1 -1; -1 -1 1; -1 -1 -1];
#  globalError = 0.1;
#  deltaError = 0.01;
#  S = [1;1;1;-1];
#Este seria para el OR
#Para el AND es lo mismo pero
#  S = [1;-1;-1;-1];
