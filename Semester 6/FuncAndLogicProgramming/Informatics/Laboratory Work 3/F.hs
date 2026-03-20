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

    let lenFromStart = lenToFirstH str
    let lenFromEnd = lenToFirstH revStr


    let start = take lenFromStart str
    let end = reverse (take lenFromEnd revStr)
    let middle = drop (lenFromEnd) (reverse (drop (firlenFromStartstLen) str))

    putStrLn $ start ++ middle ++ end