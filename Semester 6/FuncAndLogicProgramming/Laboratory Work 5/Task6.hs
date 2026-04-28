dropWhile' :: (a -> Bool) -> [a] -> [a]
dropWhile' _ [] = []
dropWhile' p (x:xs) = if p x then (dropWhile' (p) xs) else (x:xs)