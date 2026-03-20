isLeapYear :: Int -> Bool
isLeapYear num = if (num `mod` 400 == 0) then True 
    else (if (num `mod` 100 == 0) then False 
    else (if (num `mod` 4 == 0) then True else False))

main = do
    y <- getLine
    let year = read y :: Int
    putStrLn $ if (isLeapYear year) then "YES" else "NO"