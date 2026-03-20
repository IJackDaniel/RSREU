-- Finished
findRoot :: Int -> Int -> String
findRoot a b
    | a == 0 && b == 0 = "INF"
    | a == 0 = "NO"
    | b `mod` a == 0 = show (- (b `div` a))
    | otherwise = "NO"


main = do
    str <- getLine
    let number1 = read str :: Int

    str <- getLine
    let number2 = read str :: Int

    putStrLn $ findRoot number1 number2