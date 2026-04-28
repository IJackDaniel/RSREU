integralMiddle :: (Double -> Double) -> Double -> Double -> Int -> Double
integralMiddle f a b n
    | n <= 0 = error "Количество отрезков должно быть положительным"
    | otherwise = h * sum [f (a + (fromIntegral i + 0.5) * h) | i <- [0..n-1]]
    where
        h = (b - a) / fromIntegral n