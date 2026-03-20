lastDigit :: Int -> Int
lastDigit num = num `mod` 10

withoutLastDigit :: Int -> Int
withoutLastDigit num = num `div` 10

summAllIntegers :: Int -> Int
summAllIntegers 0 = 0
summAllIntegers num = lastDigit num + summAllIntegers (withoutLastDigit num)

main = do
    n <- readLn
    print $ summAllIntegers n