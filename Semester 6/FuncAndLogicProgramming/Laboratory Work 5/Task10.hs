seqA' :: Integer -> Integer
seqA' n 
    | n == 0 = -3
    | n == 1 = -2
    | n == 2 = 1
    | otherwise =
        let 
            helper a b c 2 = c 
            helper a b c n = helper b c (a - 2 * b + c) (n - 1)
        in helper (-3) (-2) 1 n