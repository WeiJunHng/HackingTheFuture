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

    // Get list of all destinations by reading the text file
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

    // Get priority queue of destinations depands on distance between the destination and the user given
    public static PriorityQueue<Destination> getDestinationQueue(User currentUser) {
        PriorityQueue<Destination> queue = new PriorityQueue<>((o1, o2) -> Double.compare(o1.distanceOf(currentUser), o2.distanceOf(currentUser)));
        queue.addAll(getDestinationList());
        return queue;
    }

    // Get "Destination" object with given name of destination
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

    // Get name of the destination
    public String getName() {
        return name;
    }

    // Get coordinate X of the destination
    public double getCoordinateX() {
        return coordinateX;
    }

    // Get coordinate Y of the destination
    public double getCoordinateY() {
        return coordinateY;
    }

    // Get distance between the destinationa and the given user
    public double distanceOf(User user) {
        double distance = Math.sqrt(Math.pow(coordinateX - user.getCoordinateX(), 2) + Math.pow(coordinateY - user.getCoordinateY(), 2));
        return Math.round(distance * 100) / 100.0;
    }

}
