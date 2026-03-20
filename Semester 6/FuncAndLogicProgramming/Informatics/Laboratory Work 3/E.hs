-- Finished
twoSteps :: Int -> [Int]
twoSteps a = helper a 1 [1]

helper :: Int -> Int -> [Int] -> [Int]
helper bound curr lst
    | 2 ^ curr > bound = lst
    | otherwise = helper bound (curr + 1) (lst ++ [fromIntegral(2 ^ curr)])

main = do
    str <- getLine
    let number = read str :: Int
    putStrLn $ unwords (map show (twoSteps number))