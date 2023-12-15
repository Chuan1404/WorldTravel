package com.worldstory.travel.services;

import com.worldstory.travel.models.HotelBooking;
import com.worldstory.travel.models.TourBooking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;

    @Value("${spring.backend.host}")
    private String host;

    public void sendEmail(TourBooking tourBooking) {

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        try {
            mailMessage.setFrom(sender);
            mailMessage.setTo(tourBooking.getEmail());
            mailMessage.setSubject(String.format("Đặt Tour - %s - %d/%d/%d",
                    tourBooking.getName(),
                    tourBooking.getDepartureDate().getDate(),
                    tourBooking.getDepartureDate().getMonth() == 0 ? 12 : tourBooking.getDepartureDate().getMonth(),
                    tourBooking.getDepartureDate().getYear()));
            mailMessage.setText(String.format("Kính gửi Quản lý,\n" +
                            "Tôi gửi email này để thông báo về việc đặt tour mới từ một khách hàng. Dưới đây là thông tin chi tiết:\n" +
                            "Tên Khách Hàng: %s\n" +
                            "Email: %s\n" +
                            "Số Điện Thoại: %s\n" +
                            "Chi tiết: %s\n" +
                            "Ngày Khởi Hành: %d/%d/%d\n" +
                            "Số Lượng Người Lớn: %d\n" +
                            "Số Lượng Trẻ Em: %d\n" +
                            "Số Lượng Em Bé: %d\n",
                    tourBooking.getName(), tourBooking.getEmail(), tourBooking.getPhone(),
                    tourBooking.getTour() != null ?
                            tourBooking.getTour().getName() + " Link: " + host + "/tour/" + tourBooking.getTour().getId() :
                            tourBooking.getDescription(),
                    tourBooking.getDepartureDate().getDate(),
                    tourBooking.getDepartureDate().getMonth() == 0 ? 12 : tourBooking.getDepartureDate().getMonth(),
                    tourBooking.getDepartureDate().getYear(),
                    tourBooking.getAdultNumber(), tourBooking.getChildNumber(), tourBooking.getBabyNumber()));

            javaMailSender.send(mailMessage);
        } catch (Exception err) {
            System.out.println(err.getMessage());
        }
    }

    public void sendEmail(HotelBooking hotelBooking) {

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        try {
            mailMessage.setFrom(sender);
            mailMessage.setTo(hotelBooking.getEmail());
            mailMessage.setSubject(String.format("Đặt Phòng - %s", hotelBooking.getName()));
            mailMessage.setText(String.format("Kính gửi Quản lý,\n" +
                            "Tôi gửi email này để thông báo về việc đặt phòng từ một khách hàng. Dưới đây là thông tin chi tiết:\n" +
                            "Tên Khách Hàng: %s\n" +
                            "Email: %s\n" +
                            "Số Điện Thoại: %s\n" +
                            "Chi tiết: %s\n" +
                            "Link: %s\n",
                    hotelBooking.getName(), hotelBooking.getEmail(), hotelBooking.getPhone(),
                    hotelBooking.getHotel().getName(), host + "/hotel/" + hotelBooking.getHotel().getId()
                    ));

            javaMailSender.send(mailMessage);
        } catch (Exception err) {
            System.out.println(err.getMessage());
        }
    }
}
