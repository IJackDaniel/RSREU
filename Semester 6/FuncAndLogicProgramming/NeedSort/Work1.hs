module Work1 where

emptyArray = []
oneArray = [1]
array = [1, 2, 3, 4, 5]

head' :: [a] -> a
head' [] = error "The list is empty"
head' (x:_) = x

tail' :: [a] -> [a]
tail' [] = error "The list is empty"
tail' (_:xs) = xs

init' :: [a] -> [a]
init' [] = error "The list is empty"
init' [a] = []
init' (x:xs) = [x] ++ init' xs

null' :: [a] -> Bool
null' [] = True
null' (_:_) = False

null'' :: (Eq a) => [a] -> Bool
null'' ls | ls == [] = True | otherwise = False


length' :: [a] -> Integer
length' [] = 0
length' (x:xs) = 1 + length' xs

length'' :: [a] -> Int
length'' s = helper s 0

helper :: [a] -> Int -> Int
helper [] len = len
helper (_:xs) len = helper xs (len + 1)

reverse' :: [a] -> [a]
reverse' [] = error "The list is empty"
reverse' [a] = [a]
reverse' (x:xs) = reverse' xs ++ [x]

get' :: [a] -> Integer -> a
get' [] n = error "Error"
get' (x:xs) n = if n < 0 then error "Error" else (if n == 0 then x else get' xs (n - 1))

conc' :: [a] -> [a] -> [a]
conc' [] arr2 = arr2
conc' arr1 arr2 = conc' (init' arr1) ((get' arr1 (length' arr1 - 1)) : arr2)

insert' :: (Ord a) => a -> [a] -> [a]
insert' x [] = [x]
insert' x (y:ys)
    | x <= y = x : y : ys
    | otherwise = y : insert' x ys

insertionSort' :: (Ord a) => [a] -> [a]
insertionSort' [] = []
insertionSort' (x:xs) = insert' x (insertionSort' xs)