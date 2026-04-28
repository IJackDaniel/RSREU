reverse' :: [a] -> [a]
reverse' [] = error "The list is empty"
reverse' [a] = [a]
reverse' (x:xs) = reverse' xs ++ [x]