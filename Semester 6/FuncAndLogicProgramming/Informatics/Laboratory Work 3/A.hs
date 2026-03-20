-- Finished
sumSeries :: Int -> Double
sumSeries n = go 0 1.0 0.0 n
    
go :: Int -> Double -> Double -> Int -> Double
go i term acc n
    | i > n = acc
    | otherwise = go (i + 1) (term / fromIntegral (i + 1)) (acc + term) n

main = do
    str <- getLine
    let number = read str :: Int
    putStrLn $ show $ sumSeries number