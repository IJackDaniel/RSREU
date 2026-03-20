-- Finished
numberOfDiv :: Int -> Int
numberOfDiv x = 
    let sqrtX = floor (sqrt (fromIntegral x))
    in 2 * length [d | d <- [1..sqrtX], x `mod` d == 0] - (if sqrtX * sqrtX == x then 1 else 0)

main = do
    number <- readLn
    putStrLn $ show $ numberOfDiv number