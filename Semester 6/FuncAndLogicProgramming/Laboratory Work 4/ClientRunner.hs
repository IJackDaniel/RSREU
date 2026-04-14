import qualified Stats as St 

main = do
    putStrLn "Input data (without brackets and commas): "
    lsStr <- getLine
    let ls = map read (words lsStr) :: [Float]

    let lsMean = St.mean ls
    let lsMedian = St.median ls
    let lsMode = St.mode ls
    let lsVariance = St.variance ls
    let lsStdDev = St.stdDev ls
    let lsVariationCoeff = St.variationCoeff ls

    putStrLn $ "Mean: " ++ show lsMean
    putStrLn $ "Median: " ++ show lsMedian
    putStrLn $ "Mode: " ++ unwords (map show lsMode)
    putStrLn $ "Variance: " ++ show lsVariance
    putStrLn $ "StdDev: " ++ show lsStdDev
    putStrLn $ "Variation coeff: " ++ show lsVariationCoeff