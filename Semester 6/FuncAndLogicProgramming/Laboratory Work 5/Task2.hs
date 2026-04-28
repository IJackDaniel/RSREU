init' :: [a] -> [a]
init' [] = error "List can not be empty"
init' [a] = []
init' (x:xs) = [x] ++ init' xs