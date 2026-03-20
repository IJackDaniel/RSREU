nDiv :: Int -> String
nDiv x = unwords (map show [d | d <- [1..x], x `mod` d == 0])

main = do
    number <- readLn
    putStrLn $ nDiv number