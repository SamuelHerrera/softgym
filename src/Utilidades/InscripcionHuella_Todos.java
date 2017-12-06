package Utilidades;

import com.digitalpersona.onetouch.DPFPDataPurpose;
import com.digitalpersona.onetouch.DPFPFeatureSet;
import com.digitalpersona.onetouch.DPFPGlobal;
import com.digitalpersona.onetouch.DPFPSample;
import com.digitalpersona.onetouch.DPFPTemplate;
import com.digitalpersona.onetouch.capture.DPFPCapture;
import com.digitalpersona.onetouch.capture.event.DPFPDataAdapter;
import com.digitalpersona.onetouch.capture.event.DPFPDataEvent;
import com.digitalpersona.onetouch.capture.event.DPFPErrorAdapter;
import com.digitalpersona.onetouch.capture.event.DPFPErrorEvent;
import com.digitalpersona.onetouch.capture.event.DPFPReaderStatusAdapter;
import com.digitalpersona.onetouch.capture.event.DPFPReaderStatusEvent;
import com.digitalpersona.onetouch.capture.event.DPFPSensorAdapter;
import com.digitalpersona.onetouch.capture.event.DPFPSensorEvent;
import com.digitalpersona.onetouch.processing.DPFPEnrollment;
import com.digitalpersona.onetouch.processing.DPFPFeatureExtraction;
import com.digitalpersona.onetouch.processing.DPFPImageQualityException;
import static com.digitalpersona.onetouch.processing.DPFPTemplateStatus.TEMPLATE_STATUS_FAILED;
import static com.digitalpersona.onetouch.processing.DPFPTemplateStatus.TEMPLATE_STATUS_READY;
import com.digitalpersona.onetouch.verification.DPFPVerification;
import com.digitalpersona.onetouch.verification.DPFPVerificationResult;
import java.awt.Frame;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import Forms.AgregarCliente;
import Forms.AgregarUsuario;
import Forms.EditarCliente;

public class InscripcionHuella_Todos {

    private DPFPFeatureSet featuresinscripcion;
    private DPFPFeatureSet featuresverificacion;
    private DPFPCapture Lector = DPFPGlobal.getCaptureFactory().createCapture();
    private DPFPEnrollment Reclutador = DPFPGlobal.getEnrollmentFactory().createEnrollment();
    private DPFPVerification Verificador = DPFPGlobal.getVerificationFactory().createVerification();
    private DPFPTemplate templateactual;
    private static String TEMPLATE_PROPERTY = "template";
    private static Object frame;

    public InscripcionHuella_Todos(final Object frame) {
        this.frame = frame;
        Iniciar();
        start();
        new Thread() {
            public void run() {
                ((Frame) frame).setVisible(true);
            }
        }.start();
    }

    private void EstadoHuellas() {
        EnviarTexto("Muestra de Huellas Necesarias para Guardar Template " + Reclutador.getFeaturesNeeded());
    }

    private void EnviarTexto(String string) {
        System.out.println(string);
    }

    private void Iniciar() {
        Lector.addDataListener(new DPFPDataAdapter() {
            @Override
            public void dataAcquired(final DPFPDataEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {

                        EnviarTexto("La Huella Digital ha sido Capturada");

                        ProcesarCaptura(e.getSample());
                        CrearImagenHuella(e.getSample());
                    }
                });
            }
        });

        Lector.addReaderStatusListener(new DPFPReaderStatusAdapter() {
            @Override
            public void readerConnected(final DPFPReaderStatusEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        EnviarTexto("El Sensor de Huella Digital esta Activado o Conectado");
                    }
                });
            }

            @Override
            public void readerDisconnected(final DPFPReaderStatusEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        EnviarTexto("El Sensor de Huella Digital esta Desactivado o no Conectado");
                    }
                });
            }
        });

        Lector.addSensorListener(new DPFPSensorAdapter() {
            @Override
            public void fingerTouched(final DPFPSensorEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        EnviarTexto("El dedo ha sido colocado sobre el Lector de Huella ----segunda");
                    }
                });
            }

            @Override
            public void fingerGone(final DPFPSensorEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        EnviarTexto("El dedo ha sido quitado del Lector de Huella-------segunda");
                    }
                });
            }
        });

        Lector.addErrorListener(new DPFPErrorAdapter() {
            public void errorReader(final DPFPErrorEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        EnviarTexto("Error: " + e.getError());
                    }
                });
            }
        });
    }

    private DPFPFeatureSet extraerCaracteristicas(DPFPSample sample, DPFPDataPurpose purpose) {
        DPFPFeatureExtraction extractor = DPFPGlobal.getFeatureExtractionFactory().createFeatureExtraction();
        try {
            return extractor.createFeatureSet(sample, purpose);
        } catch (DPFPImageQualityException e) {
            return null;
        }
    }

    private void ProcesarCaptura(DPFPSample sample) {
        featuresinscripcion = extraerCaracteristicas(sample, DPFPDataPurpose.DATA_PURPOSE_ENROLLMENT);
        featuresverificacion = extraerCaracteristicas(sample, DPFPDataPurpose.DATA_PURPOSE_VERIFICATION);
        if (featuresinscripcion != null) {
            try {

                Reclutador.addFeatures(featuresinscripcion);
            } catch (DPFPImageQualityException ex) {
                System.err.println("Error: " + ex.getMessage());
            } finally {
                switch (Reclutador.getTemplateStatus()) {
                    case TEMPLATE_STATUS_READY:
                        stop();
                        setTemplate(Reclutador.getTemplate());

                        SoftGym.hu.stop();
                        System.out.println("se detuvo hu");
                        SoftGym.syd.start();

                        //__________________________________________________________________
                        try {
                            usadb db = new usadb();
                            Object[][] huellas = db.getAll_HuellaCliente();
                            for (int index = 0; huellas != null && index < huellas.length; index++) {
                                byte templateBuffer[] = (byte[]) huellas[index][1];
                                DPFPTemplate referenceTemplate = DPFPGlobal.getTemplateFactory().createTemplate(templateBuffer);
                                setTemplate(referenceTemplate);

                                DPFPVerificationResult result = Verificador.verify(featuresverificacion, referenceTemplate);

                                if (result.isVerified()) {
                                    //System.out.println("se encontro la huella digital ID:" +huellas[index][0]);
                                    if (frame.getClass().equals(AgregarUsuario.class)) {
                                        ((AgregarUsuario) frame).dispose();
                                        JOptionPane.showMessageDialog((AgregarUsuario) frame, "El cliente ya existe en la base de datos");
                                    } else {
                                        if (frame.getClass().equals(EditarCliente.class)) {
                                            ((EditarCliente) frame).dispose();
                                            JOptionPane.showMessageDialog((EditarCliente) frame, "El cliente ya existe en la base de datos");
                                        } else {
                                            ((AgregarCliente) frame).dispose();
                                            AgregarCliente.bandera = true;
                                            JOptionPane.showMessageDialog((AgregarCliente) frame, "El cliente ya existe en la base de datos");
                                        }
                                    }
                                    return;
                                }

                            }
                            //SoftGym.prin.BusquedaCompleta(-1);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }

                        setTemplate(Reclutador.getTemplate());

                        //__________________________________________________________________
                        if (frame.getClass().equals(AgregarUsuario.class)) {
                            ((AgregarUsuario) frame).setTemplate(templateactual);
                            JOptionPane.showMessageDialog((AgregarUsuario) frame, "La Plantilla de la Huella ha Sido Creada");
                        } else {
                            if (frame.getClass().equals(EditarCliente.class)) {
                                ((EditarCliente) frame).setTemplate(templateactual);
                                JOptionPane.showMessageDialog((EditarCliente) frame, "La Plantilla de la Huella ha Sido Creada");
                            } else {
                                ((AgregarCliente) frame).setTemplate(templateactual);
                                JOptionPane.showMessageDialog((AgregarCliente) frame, "La Plantilla de la Huella ha Sido Creada");
                            }
                        }
                        break;

                    case TEMPLATE_STATUS_FAILED:
                        Reclutador.clear();
                        stop();
                        setTemplate(null);
                        EnviarTexto("La Plantilla de la Huella no pudo ser creada, Repita el Proceso");
                        start();
                        break;
                }
            }
        }
    }

    public void start() {
        try {
            Lector.startCapture();
        } catch (Exception ex) {
            System.out.println("Error en IDY 194, sin mayor problema");
        }
    }

    private void setTemplate(DPFPTemplate template) {
        this.templateactual = template;
    }

    private void CrearImagenHuella(DPFPSample sample) {
        if (frame.getClass().equals(AgregarUsuario.class)) {
            ((AgregarUsuario) frame).jLabel3.setIcon(new ImageIcon(DPFPGlobal.getSampleConversionFactory().createImage(sample).getScaledInstance(123, 161, Image.SCALE_SMOOTH)));
        } else {
            if (frame.getClass().equals(EditarCliente.class)) {
                ((EditarCliente) frame).jLabel3.setIcon(new ImageIcon(DPFPGlobal.getSampleConversionFactory().createImage(sample).getScaledInstance(123, 161, Image.SCALE_SMOOTH)));
            } else {
                ((AgregarCliente) frame).jLabel3.setIcon(new ImageIcon(DPFPGlobal.getSampleConversionFactory().createImage(sample).getScaledInstance(123, 161, Image.SCALE_SMOOTH)));
            }
        }
    }

    public void stop() {
        Lector.stopCapture();
    }

    private DPFPTemplate getTemplate() {
        return templateactual;
    }

}
