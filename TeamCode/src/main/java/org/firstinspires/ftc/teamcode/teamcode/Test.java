/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcontroller.external.samples.HardwarePushbot;

/**
 * This file provides basic Telop driving for a Pushbot robot.
 * The code is structured as an Iterative OpMode
 *
 * This OpMode uses the common Pushbot hardware class to define the devices on the robot.
 * All device access is managed through the HardwarePushbot class.
 *
 * This particular OpMode executes a basic Tank Drive Teleop for a PushBot
 * It raises and lowers the claw using the Gampad Y and A buttons respectively.
 * It also opens and closes the claws slowly using the left and right Bumper buttons.
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@TeleOp(name="Test", group="Pushbot")

public class Test extends OpMode{

    /* Declare OpMode members. */
    public DcMotor leftfront; //declaring motors
    public DcMotor leftback;
    public DcMotor rightfront;
    public DcMotor rightback;

    public DcMotor leftintake; //names of motors
    public DcMotor rightintake;

    public DcMotor lift;

    public Servo catcherleft;
    public Servo catcherright;

    public Servo liftright;
    public Servo liftleft;




    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        /* Initialize the hardware variables.
         * The init() method of the hardware class does all the work here
         */
        leftback = hardwareMap.dcMotor.get("leftback"); //initilizing names of motors
        leftfront = hardwareMap.dcMotor.get("leftfront");
        rightback = hardwareMap.dcMotor.get("rightback");
        rightfront = hardwareMap.dcMotor.get("rightfront");

        rightback.setDirection(DcMotorSimple.Direction.REVERSE); //these reverse are for mecanum wheels
        rightfront.setDirection(DcMotorSimple.Direction.REVERSE); // bc of they way they work u know

        leftintake = hardwareMap.dcMotor.get("leftintake");
        rightintake = hardwareMap.dcMotor.get("rightintake");

        rightintake.setDirection(DcMotorSimple.Direction.REVERSE);

        lift = hardwareMap.dcMotor.get("lift");

        catcherleft = hardwareMap.servo.get("catcherleft");
        catcherright  = hardwareMap.servo.get("catcherright");

        catcherright.setDirection(Servo.Direction.REVERSE);

        liftright = hardwareMap.servo.get("liftright");
        liftleft = hardwareMap.servo.get("liftleft");

        liftright.setDirection(Servo.Direction.REVERSE);

        // Send telemetry message to signify robot waiting;

    }
    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void init_loop() {
    }

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {
    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {
       //double x = 0;
       //double y = 0;
       //double z = 0;
        double leftSideDrive = 0; //number value placeholder
        double rightSideDrive = 0;

        double intakePower = 0;
        double reversePower = 0;

        double liftPower = 0;


        if(Math.abs(gamepad1.left_stick_y)> 0.1)
            rightSideDrive = gamepad1.left_stick_y;


        if (Math.abs(gamepad1.right_stick_y)> 0.1)
            leftSideDrive = gamepad1.right_stick_y;


        if (Math.abs(gamepad1.left_trigger)> 0.1)
            intakePower = gamepad1.left_trigger;

        else if(gamepad1.left_bumper) //true or false value
            intakePower = -0.5; //use neg numbers to reverse


        if (Math.abs(gamepad1.right_trigger)> 0.1)
            intakePower = gamepad1.right_trigger;

        else if(gamepad1.right_bumper)
            intakePower = -0.5;


        if (Math.abs(gamepad2.left_stick_y)> 0.1)
            liftPower = gamepad2.left_stick_y;


        if (gamepad1.a) {
            catcherright.setPosition(0);
            catcherleft.setPosition(0);
        }

        else if (gamepad1.b) {
            catcherright.setPosition(1);
            catcherleft.setPosition(1);
        }

        if (gamepad2.right_trigger>0.1){
            liftright.setPosition(0);
            liftleft.setPosition(0);
        }

        else if (gamepad2.left_trigger>0.1) {
            liftright.setPosition(1);
            liftleft.setPosition(1);
        }

        else if (gamepad2.right_bumper||gamepad2.left_bumper) {
            liftright.setPosition(0.4);
            liftleft.setPosition(0.5);
        }

        leftback.setPower(leftSideDrive);
        leftfront.setPower(leftSideDrive);
        rightback.setPower(rightSideDrive);
        rightfront.setPower(rightSideDrive);
        leftintake.setPower(intakePower);
        rightintake.setPower(intakePower);
        leftintake.setPower(reversePower);
        rightintake.setPower(reversePower);
        lift.setPower(-liftPower);


        // Run wheels in tank mode (note: The joystick goes negative when pushed forwards, so negate it)


        // Use gamepad left & right Bumpers to open and close the claw


        // Move both servos to new position.  Assume servos are mirror image of each other.


        // Use gamepad buttons to move the arm up (Y) and down (A)


        // Send telemetry message to signify robot running;

        //if nothing works, make sure to disable instant run

    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
    }
}
