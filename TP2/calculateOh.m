function x = calculateOh(matrix, row, size, w) 
  h = (matrix(row,1:size))*w;
  #SIGN(H) es para la funcion de activacion ESCALON
  x = sign(h);
endfunction