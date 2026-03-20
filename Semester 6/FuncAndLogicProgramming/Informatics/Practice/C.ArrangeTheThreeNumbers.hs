sortTwoDigit :: Integer -> Integer -> [Integer]
sortTwoDigit b c
    | b > c = [c, b]
    | otherwise = [b, c]

sortThreeDigit :: Integer -> [Integer] -> [Integer]
sortThreeDigit a [b, c]
    | a < b = [a, b, c]
    | a > c = [b, c, a]
    | otherwise = [b, a, c]

main = do
    n1 <- readLn
    n2 <- readLn
    n3 <- readLn
    let sorted = sortThreeDigit n1 (sortTwoDigit n2 n3)
    putStrLn $ unwords (map show sorted)