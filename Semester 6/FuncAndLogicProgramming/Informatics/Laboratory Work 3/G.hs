-- Finished
get' :: Int -> [Int] -> Int
get' n (x:xs)
    | n == 0 = x
    | otherwise = get' (n-1) xs

moreThen :: [Int] -> Int
moreThen array = helper array 1 0

helper :: [Int] -> Int -> Int -> Int
helper array index count
    | index >= ((length array) - 1) = count
    | get' index array > get' (index - 1) array && get' index array > get' (index + 1) array = helper array (index + 2) (count + 1)
    | otherwise = helper array (index + 1) count

main = do
    str <- getLine
    let ls = map read (words str) :: [Int]

    putStrLn $ show $ moreThen ls