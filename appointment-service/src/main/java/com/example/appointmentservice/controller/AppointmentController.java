package com.example.appointmentservice.controller;

import com.example.appointmentservice.dto.AppointmentDto;
import com.example.appointmentservice.dto.ResourceAllocationDto;
import com.example.appointmentservice.dto.SearchDto;
import com.example.appointmentservice.dto.SlotDto;
import com.example.appointmentservice.entity.Medicine;
import com.example.appointmentservice.entity.Slot;
import com.example.appointmentservice.service.AppointmentService;
import com.example.appointmentservice.service.MedicineService;
import com.example.appointmentservice.service.ResourceAllocationService;
import com.example.appointmentservice.service.SlotService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v2")
@AllArgsConstructor
public class AppointmentController {

    @Autowired
    private SlotService slotService;
    @Autowired
    private ResourceAllocationService resourceAllocationService;
    @Autowired
    private AppointmentService appointmentService;
    private MedicineService medicineService;

    @PostMapping("/create/slots")
    public ResponseEntity<SlotDto> createSlots(@RequestBody SlotDto slotDto)
    {
        SlotDto slotDto1=slotService.createSlots(slotDto);
        return new ResponseEntity<>(slotDto1, HttpStatus.OK);
    }
    @PostMapping("/cancel/slots/{id}")
    public ResponseEntity<String> cancelSlots(@PathVariable long id)
    {
        slotService.cancelSlot(id);
        return new ResponseEntity<>("Slot is canceled",HttpStatus.OK);
    }

    @PostMapping("/create/resource/{id}")
    public ResponseEntity<String> createResource(@RequestBody ResourceAllocationDto resourceAllocationDto,@PathVariable long id)
    {
        resourceAllocationService.createResource(resourceAllocationDto,id);
        return new ResponseEntity<>("Resource Allocated Successfully",HttpStatus.OK);
    }

    @PostMapping("/create/appointment/{id}")
    public ResponseEntity<String> createAppointment(@PathVariable long id)
    {
        appointmentService.createAppointment(id);
        return new ResponseEntity<>("Appointment created Successfully",HttpStatus.OK);
    }

    @PostMapping("/cancel/appointment/{id}")
    public ResponseEntity<String> cancelAppointment(@PathVariable long id)
    {
        appointmentService.cancelAppointment(id);
        return new ResponseEntity<>("Appointment cancelled successfully",HttpStatus.OK);
    }

    @GetMapping("/getAllAppointmentByPatientId")
    public ResponseEntity<List<AppointmentDto>> getAllAppointmentByPatientId()
    {
        List<AppointmentDto>appointmentDtos=appointmentService.getAppointmentByPatientId();
        return new ResponseEntity<>(appointmentDtos,HttpStatus.OK);
    }

    @GetMapping("/getAllAppointmentByDoctorId")
    public ResponseEntity<List<AppointmentDto>> getAllAppointmentByDoctorId()
    {
        List<AppointmentDto>appointmentDtos=appointmentService.getAppointmentByDoctorId();
        return new ResponseEntity<>(appointmentDtos,HttpStatus.OK);
    }

    @GetMapping("/getAllAppointments")
    public ResponseEntity<List<AppointmentDto>> getAllAppointments()
    {
        List<AppointmentDto>appointmentDtos=appointmentService.getAllAppointments();
        return new ResponseEntity<>(appointmentDtos,HttpStatus.OK);
    }

    @PostMapping("/create/medicine")
    public ResponseEntity<String> createMedicine(@RequestBody Medicine medicine)
    {
        medicineService.createMedicine(medicine);
        return new ResponseEntity<>("Medicine Added ",HttpStatus.OK);
    }

    @PutMapping("/update/medicine/{id}")
    public ResponseEntity<String> updateMedicine(@RequestBody Medicine medicine,@PathVariable long id)
    {
        medicineService.updateMedicine(medicine,id);
        return new ResponseEntity<>("Updated Successfully",HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/delete/medicine/{id}")
    public ResponseEntity<String> deleteMedicine(@PathVariable long id)
    {
        medicineService.deleteMedicine(id);
        return new ResponseEntity<>("Deleted Successfully",HttpStatus.OK);
    }

    @GetMapping("/getAllMedicine")
    public ResponseEntity<List<Medicine>>getAllMedicine()
    {
        List<Medicine>medicines=medicineService.getAllMedicine();
        return new ResponseEntity<>(medicines,HttpStatus.OK);
    }

    @GetMapping("/getMedicineById/{id}")
    public ResponseEntity<Medicine> getMedicineById(@PathVariable long id)
    {
        Medicine medicine=medicineService.getMedicineById(id);
        return new ResponseEntity<>(medicine,HttpStatus.OK);
    }

    @GetMapping("/getAllSlotsByDoctorId/{id}")
    public ResponseEntity<List<Slot>> getAllSlotsByDoctorId(@PathVariable long id)
    {
        List<Slot> slotDtos=slotService.getAllSlotsByDoctorId(id);
        return new ResponseEntity<>(slotDtos,HttpStatus.OK);
    }

    @GetMapping("/getMyAllSlots")
    public ResponseEntity<List<Slot>> getMyAllSlots()
    {
        List<Slot>slotDtos=slotService.getMyAllSlots();
        return new ResponseEntity<>(slotDtos,HttpStatus.OK);
    }
    @GetMapping("/getAllSlots")
    public ResponseEntity<List<SearchDto>> getAllSlots()
    {
        List<SearchDto>searchDtos=slotService.getAllSlots();
        return new ResponseEntity<>(searchDtos,HttpStatus.OK);
    }
}
