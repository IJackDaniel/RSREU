-- Finished
lenToFirstH :: String -> Int
lenToFirstH str = helper str 0

helper :: String -> Int -> Int
helper str index
    | index == 0 = if (last(take 1 str) == 'h') then 1 else helper str (index + 1)
    | last (take index str) == 'h' = index
    | otherwise = helper str (index + 1)

main = do
    str <- getLine
    let revStr = reverse str

    let firstLen = lenToFirstH str
    let secondLen = lenToFirstH revStr


    let first = take firstLen str
    let second = reverse (take secondLen revStr)
    let middle = drop (secondLen) (reverse (drop (firstLen) str))

    putStrLn $ first ++ middle ++ second