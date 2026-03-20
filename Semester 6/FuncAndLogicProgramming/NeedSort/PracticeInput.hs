module LectionInput where
-- :load LectionInput.hs

-- hello = putStrLn "Hello, 3413!"

-- Задача 1. Дано трёхзначное число, найти сумму его цифр
lastDigit :: Int -> Int
lastDigit num = num `mod` 10

withoutLastDigit :: Int -> Int
withoutLastDigit num = num `div` 10

summAllIntegers :: Int -> Int
summAllIntegers 0 = 0
summAllIntegers num = lastDigit num + summAllIntegers (withoutLastDigit num)

-- Вариант 1
-- getLine возвращает IO String
-- полученную строку мы преобразуем строку в Read, а потом явно преобразуем к Int
-- Затем приводим Int к строке и выводим
main1 = do
    str <- getLine
    let n = read str :: Int
    putStrLn $ show $ summAllIntegers n


-- Вариант 2
main2 = do
    n <- readLn
    print $ summAllIntegers n

-------------------------------------------------------------------------------------

-- mult :: Float -> Float
-- mult num = num * 2

-- main = do
--     n <- readLn
--     print $ mult n

-------------------------------------------------------------------------------------
-- Задание 2. Определение високосного года. Вывод: YES или NO

isLeapYear :: Int -> Bool
isLeapYear num = if (num `mod` 400 == 0) then True 
    else (if (num `mod` 100 == 0) then False 
    else (if (num `mod` 4 == 0) then True else False))

-- main = do
--     y <- getLine
--     let year = read y :: Int
--     putStrLn $ if (isLeapYear year) then "YES" else "NO"

-------------------------------------------------------------------------------------
-- Задание 3. Упорядочить три числа
min' a b c
    | a < b && a < c = a
    | b < a && b < c = b
    | otherwise = c

max' a b c
    | a > b && a > c = a
    | b > a && b > c = b
    | otherwise = c

sum' a b c = a + b + c

-- main = do
--     n1 <- readLn
--     n2 <- readLn
--     n3 <- readLn
--     print $ "Result"
--     print $ min' n1 n2 n3 
--     print $ sum' n1 n2 n3 - min' n1 n2 n3 - max' n1 n2 n3 
--     print $ max' n1 n2 n3

-- Задание 4. Вывести все делители числа
nDiv :: Int -> String
nDiv x = unwords (map show [d | d <- [1..x], x `mod` d == 0])

main = do
    number <- readLn
    putStrLn $ nDiv number