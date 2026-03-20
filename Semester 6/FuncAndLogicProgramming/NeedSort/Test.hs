module Test where

seqA' :: Integer -> Integer
seqA' n 
    | n < 3 = n + 1
    | otherwise =
        let 
            helper a b c 2 = c 
            helper a b c n = helper b c (c + b - 2 * a) (n - 1)
        in helper 1 2 3 n

isPrime :: Integer -> Bool
isPrime p
    | p <= 0 = error "..."
    | p == 1 = False
    | otherwise = isPrime' 2 p
isPrime' d p
    | d * d > p = True
    | mod p d == 0 = False
    | otherwise = isPrime' (d+1) p