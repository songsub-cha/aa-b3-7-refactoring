import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class VRLogic {// Seperate Domain from Presentation - UI / Logic (Customer / Video)
    private List<Customer> customers = new ArrayList<Customer>();
    private List<Video> videos = new ArrayList<Video>();

    public VRLogic() {
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public void addVideo(Video video) {
        videos.add(video);
    }

    public List<Customer> getCustomers() {
        return Collections.unmodifiableList(customers);
    }

    public List<Video> getVideos() {
        return Collections.unmodifiableList(videos);
    }

    public Customer findCustomer(String customerName) {
        Customer foundCustomer = null;
        for (Customer customer : customers) {
            if (customer.getName().equals(customerName)) {
                foundCustomer = customer;
                break;
            }
        }
        return foundCustomer;
    }

    private Rental findRental(Customer customer, String videoTitle) {
        Rental foundRental = null;
        List<Rental> customerRentals = customer.getRentals();
        for (Rental rental : customerRentals) {
            // customer 의 rental 목록에서 해당 빌력간 video 가 있는지 찾는다.
            if (rental.getVideo().getTitle().equals(videoTitle) && rental.getVideo().isRented()) {
                foundRental = rental;
                break;
            }
        }
        return foundRental;
    }

    private Video findVideo(String videoTitle) {
        Video foundVideo = null;
        for (Video video : videos) {
            if (video.getTitle().equals(videoTitle) && video.isRented() == false) {
                foundVideo = video;
                break;
            }
        }
        return foundVideo;
    }

    public Customer clearRentals(String customerName) {
        Customer foundCustomer = findCustomer(customerName);
        if (foundCustomer == null) return null;

        List<Rental> rentals = new ArrayList<Rental>();
        foundCustomer.setRentals(rentals);

        return foundCustomer;
    }

    public void returnVideo(String customerName, String videoTitle) {
        Customer foundCustomer = findCustomer(customerName);
        if (foundCustomer == null) return;

        Rental foundRental = findRental(foundCustomer, videoTitle);
        if (foundRental == null) return;

        foundRental.returnVideo();
        foundRental.getVideo().setRented(false);
    }

    public void rentVideo(String customerName, String videoTitle) {
        Customer foundCustomer = findCustomer(customerName);
        if (foundCustomer == null) return;

        Video foundVideo = findVideo(videoTitle);
        if (foundVideo == null) return;

        Rental rental = new Rental(foundVideo);
        foundVideo.setRented(true);

        // encapsulate collection
        List<Rental> customerRentals = foundCustomer.getRentals();
        customerRentals.add(rental);
        foundCustomer.setRentals(customerRentals);
    }
}
