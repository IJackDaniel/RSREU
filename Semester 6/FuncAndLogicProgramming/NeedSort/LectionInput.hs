import System.Random


-- Разбираем вывод и ввод
hello :: String -> String
hello name = "Hello, " ++ name ++ "!"

main1 = do
    putStrLn "What is your name?"
    name <- getLine
    let hlname = hello name
    putStrLn hlname

-- Найти гипотенузу
hypot :: Double -> Double -> Double
hypot a b = sqrt (a * a + b * b)

main21 = do
    putStrLn "Input first catet:"
    str <- getLine
    let firstCatet = read str :: Double

    putStrLn "Input second catet:"
    str <- getLine
    let secondCatet = read str :: Double

    putStrLn "Gipotenuza: "
    let hyp = hypot firstCatet secondCatet
    putStrLn $ show (hyp)

main22 = do
    putStrLn "Input first catet:"
    firstCatet <- readLn

    putStrLn "Input second catet:"
    secondCatet <- readLn

    print $ hypot firstCatet secondCatet

-- Вводится список чисел в одной строке. Увеличьте его значение в два раза. Выведите его в виде списка
doubleValue :: [Int] -> [Int]
doubleValue ls = map (\ x -> 2 * x) ls

-- функция words позволяет распарсить по пробелам
main3 = do
    lsStr <- getLine
    let ls = map read (words lsStr) :: [Int]
    print $ doubleValue ls

-- Вывести спискок, обнулить элементы на чётных позициях и вывести без []
modif :: [Int] -> [Int]
modif ls = zipWith (\ a b -> a * b) ls (cycle [1, 0])

-- в единую строчку собрали с помощью unwords
main4 = do
    lsStr <- getLine
    let ls = map read (words lsStr) :: [Int]
    putStrLn $ unwords $ map show $ modif ls

-- Бросание игральной кости
-- В самом верху импортировали System.Random

minDice :: Int
minDice = 1

maxDice :: Int
maxDice = 6

main5 = do
    dice <- randomRIO (minDice, maxDice)
    putStrLn $ show dice