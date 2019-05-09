package com.btp.fji;

import com.btp.fji.service.HeatDissipatorService;
import com.btp.fji.util.PgmReader;
import java.util.Objects;

public class App {

    public static void main(String[] args) {
        Integer iterations = null;
        Double minDelta = null;

        if (args.length != 4) {
            System.err.println("Invalid amount of arguments");
            System.exit(1);
        }

        String option = args[2];

        if (option.equals("-m")) {
            minDelta = Double.parseDouble(args[3]);
        } else if (option.equals("-i")) {
            iterations = Integer.parseInt(args[3]);
        } else {
            System.err.println("Valid end condition missing");
            System.exit(1);
        }

        double[][] heat = PgmReader.read(System.class.getResource("/" + args[0]).getPath());
        double[][] cond = PgmReader.read(System.class.getResource("/" + args[1]).getPath());

        HeatDissipatorService service = new HeatDissipatorService(heat, cond);

        if (Objects.nonNull(iterations)) {
            service.run(iterations, false);
        } else {
            service.run(minDelta, false);
        }
    }

}
