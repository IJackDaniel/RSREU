-- Finished
check :: Int -> Int -> Int -> String
check n m k
    | k >= (n * m) = "NO"
    | k `mod` m == 0 || k `mod` n == 0 = "YES"
    | otherwise = "NO"

main = do
    number1 <- readLn
    number2 <- readLn
    number3 <- readLn
    putStrLn $ check number1 number2 number3