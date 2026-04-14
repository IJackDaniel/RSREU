module Stats
(
    mean,
    median,
    mode,
    variance,
    stdDev,
    variationCoeff
 ) where

mean :: [Float] -> Float
mean [] = 0.0
mean arr = sum arr / fromIntegral (length arr)

median :: [Float] -> Float
median [] = 0.0
median arr =
    let sorted = sort arr
        len = length sorted
    in if odd len
        then sorted !! (len `div` 2)
        else (sorted !! (len `div` 2 - 1) + sorted !! (len `div` 2)) / 2

mode :: [Float] -> [Float]
mode [] = []
mode arr =
    let freqMap = countFrequencies arr
        maxFreq = maximum (map snd freqMap)
    in map fst (filter (\(_, freq) -> freq == maxFreq) freqMap)

variance :: [Float] -> Float
variance [] = 0.0
variance arr = 
    let m = mean arr
        sqDiffs = map (\x -> (x - m) ^ 2) arr
    in sum sqDiffs / fromIntegral (length arr)

stdDev :: [Float] -> Float
stdDev [] = 0.0
stdDev arr = sqrt (variance arr)

variationCoeff :: [Float] -> Float
variationCoeff arr
    | null arr = 0.0
    | m == 0 = 0.0 
    | otherwise = (s / abs m) * 100
    where
        m = mean arr
        s = stdDev arr

-- Вспомогательные функции

countFrequencies :: [Float] -> [(Float, Int)]
countFrequencies arr = 
    let grouped = group (sort arr)
    in map (\g -> (head g, length g)) grouped

sort :: [Float] -> [Float]
sort [] = []
sort (x:xs) = sort [a | a <- xs, a <= x] ++ [x] ++ sort [a | a <- xs, a > x]

group :: Eq a => [a] -> [[a]]
group [] = []
group (x:xs) = (x : takeWhile (==x) xs) : group (dropWhile (==x) xs)