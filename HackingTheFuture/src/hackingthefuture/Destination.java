/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hackingthefuture;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

/**
 *
 * @author Asus
 */
public class Destination {

    private static final List<Destination> destinationList = new ArrayList<>();

    private final String name;
    private final double coordinateX;
    private final double coordinateY;

    public static List<Destination> getDestinationList() {
        destinationList.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader("BookingDestination.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) {
                    continue;
                }
                String[] coordinates = reader.readLine().split("\\s*,\\s*");
                destinationList.add(new Destination(line, Double.parseDouble(coordinates[0]), Double.parseDouble(coordinates[1])));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return destinationList;
    }

    public static PriorityQueue<Destination> getDestinationQueue(User currentUser) {
        PriorityQueue<Destination> queue = new PriorityQueue<>((o1, o2) -> Double.compare(o1.distanceOf(currentUser), o2.distanceOf(currentUser)));
        queue.addAll(getDestinationList());
        return queue;
    }

    public static Destination getDestination(String destinationName) {
        for (Destination destination : getDestinationList()) {
            if (destination.getName().equals(destinationName)) {
                return destination;
            }
        }
        return null;
    }

    public Destination(String name, double X, double Y) {
        this.name = name;
        this.coordinateX = X;
        this.coordinateY = Y;
    }

    public String getName() {
        return name;
    }

    public double getCoordinateX() {
        return coordinateX;
    }

    public double getCoordinateY() {
        return coordinateY;
    }

    public double distanceOf(User user) {
        double distance = Math.sqrt(Math.pow(coordinateX - user.getCoordinateX(), 2) + Math.pow(coordinateY - user.getCoordinateY(), 2));
        return Math.round(distance * 100) / 100.0;
    }

}
