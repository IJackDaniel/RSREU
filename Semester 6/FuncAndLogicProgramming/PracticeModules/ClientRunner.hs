import qualified Geometry.Sphere as Sphere
import qualified Geometry.Cuboid as Cuboid
import qualified Geometry.Cube as Cube
import qualified Geometry as Geometry

main = do
    putStrLn "Sphere radius:"
    str <- getLine
    let sphereRadius = read str :: Float
    putStrLn "Volume:"
    putStrLn $ show $ Sphere.volume sphereRadius
    putStrLn $ show $ Geometry.sphereVolume sphereRadius

    putStrLn "\nSides of Cuboid"
    putStrLn "First:"
    str <- getLine
    let firstSide = read str :: Float
    putStrLn "Second:"
    str <- getLine
    let secondSide = read str :: Float
    putStrLn "Third:"
    str <- getLine
    let thirdSide = read str :: Float
    putStrLn "Area of cuboid:"
    putStrLn $ show $ Cuboid.area firstSide secondSide thirdSide  
    putStrLn $ show $ Geometry.cuboidArea firstSide secondSide thirdSide     

    putStrLn "\nSide of Cube: "
    str <- getLine
    let side = read str :: Float
    putStrLn "Area of Cube:"
    putStrLn $ show $ Cube.area side
    putStrLn $ show $ Geometry.cubeArea side

    