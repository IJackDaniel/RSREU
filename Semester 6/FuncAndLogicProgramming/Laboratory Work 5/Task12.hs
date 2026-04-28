findRoot :: Double -> Double -> (Double -> Double) -> Double
findRoot l r func
    | abs(func l) < 0.001 = l
    | otherwise = if ((func l) * (func ((r + l) / 2)) < 0) then findRoot l ((r + l) / 2) func else (findRoot ((r + l) / 2) r func)