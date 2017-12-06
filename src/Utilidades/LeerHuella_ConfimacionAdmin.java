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
import com.digitalpersona.onetouch.verification.DPFPVerification;
import com.digitalpersona.onetouch.verification.DPFPVerificationResult;
import static java.awt.Frame.ICONIFIED;
import static java.awt.Frame.NORMAL;
import java.awt.Image;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import Forms.Dialogo_Minimizar;

public class LeerHuella_ConfimacionAdmin {

    private DPFPCapture Lector = DPFPGlobal.getCaptureFactory().createCapture();
    private DPFPEnrollment Reclutador = DPFPGlobal.getEnrollmentFactory().createEnrollment();
    private DPFPVerification Verificador = DPFPGlobal.getVerificationFactory().createVerification();
    private DPFPTemplate template;
    public static String TEMPLATE_PROPERTY = "template";
    public DPFPFeatureSet featuresinscripcion;
    public DPFPFeatureSet featuresverificacion;
    private JFrame frame;

    public LeerHuella_ConfimacionAdmin(JFrame frame) {
        this.frame = frame;
        frame.setVisible(true);
        Iniciar();
        start();
    }

    public void start() {
        Lector.startCapture();
    }

    public void stop() {
        Lector.stopCapture();
    }

    private void Iniciar() {
        Lector.addDataListener(new DPFPDataAdapter() {
            @Override
            public void dataAcquired(final DPFPDataEvent e) {
                java.awt.EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        ProcesarCaptura(e.getSample());
                        Image im = DPFPGlobal.getSampleConversionFactory().createImage(e.getSample());
                        // SoftGym.prin.jLabel1.setIcon(new ImageIcon(im.getScaledInstance(83, 83, Image.SCALE_SMOOTH)));

                        try {
                            identificarHuella();
                            Reclutador.clear();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                });
            }
        });

        Lector.addReaderStatusListener(
                new DPFPReaderStatusAdapter() {
            @Override
            public void readerConnected(
                    final DPFPReaderStatusEvent e) {
                java.awt.EventQueue.invokeLater(
                        new Runnable() {
                    public void run() {
                    }
                });
            }

            @Override
            public void readerDisconnected(final DPFPReaderStatusEvent e) {
                java.awt.EventQueue.invokeLater(
                        new Runnable() {
                    public void run() {
                    }
                }
                );
            }
        });

        Lector.addSensorListener(
                new DPFPSensorAdapter() {
            @Override
            public void fingerTouched(final DPFPSensorEvent e) {
                java.awt.EventQueue.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                    }
                });
            }

            @Override
            public void fingerGone(final DPFPSensorEvent e) {
                java.awt.EventQueue.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                    }
                });
            }
        });

        Lector.addErrorListener(new DPFPErrorAdapter() {
            public void errorReader(final DPFPErrorEvent e) {
                java.awt.EventQueue.invokeLater(new Runnable() {
                    @Override
                    public void run() {
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
                //System.err.println("Error: "+ex.getMessage());
            } finally {
                switch (Reclutador.getTemplateStatus()) {
                    case TEMPLATE_STATUS_READY:
                        setTemplate(Reclutador.getTemplate());
                        break;

                    case TEMPLATE_STATUS_FAILED:
                        Reclutador.clear();
                        stop();
                        setTemplate(null);
                        //JOptionPane.showMessageDialog(null, "La Plantilla de la Huella no pudo ser creada, Repita el Proceso", "Inscripcion de Huellas Dactilares", JOptionPane.ERROR_MESSAGE);
                        start();
                        break;
                }
            }
        }
    }

    private void setTemplate(DPFPTemplate template) {
        DPFPTemplate old = this.template;
        this.template = template;
    }

    private Image CrearImagenHuella(DPFPSample sample) {
        return DPFPGlobal.getSampleConversionFactory().createImage(sample);
    }

    private DPFPTemplate getTemplate() {
        return template;
    }

    private void identificarHuella() throws IOException {
        try {
            usadb db = new usadb();
            Object[][] huellas = db.getAll_HuellaUsuario();
            for (int index = 0; huellas != null && index < huellas.length; index++) {
                byte templateBuffer[] = (byte[]) huellas[index][1];
                DPFPTemplate referenceTemplate = DPFPGlobal.getTemplateFactory().createTemplate(templateBuffer);
                setTemplate(referenceTemplate);
                DPFPVerificationResult result = Verificador.verify(featuresverificacion, getTemplate());

                if (result.isVerified()) {
                    Object[][] usuario = db.get_usuario(huellas[index][0], "idusuario");
                    if (usuario[0][3].toString().equals("administrador")) {

                        Dialogo_Minimizar v = (Dialogo_Minimizar) frame;
                        switch (v.x) {
                            case "ventana":
                                SoftGym.prin.removeNotify();
                                SoftGym.prin.setUndecorated(false);
                                SoftGym.prin.setLocationRelativeTo(null);
                                SoftGym.prin.setExtendedState(NORMAL);
                                SoftGym.prin.setSize(600, 400);
                                SoftGym.prin.addNotify();
                                break;

                            case "minimizar":
                                SoftGym.prin.removeNotify();
                                SoftGym.prin.setUndecorated(false);
                                SoftGym.prin.setLocationRelativeTo(null);
                                SoftGym.prin.setExtendedState(NORMAL);
                                SoftGym.prin.setSize(600, 400);
                                SoftGym.prin.addNotify();
                                SoftGym.prin.setExtendedState(ICONIFIED);

                                break;

                            case "cerrar":
                                int resp = JOptionPane.showConfirmDialog(frame, "Desea cerrar el administrador?", "Aviso de cierre", JOptionPane.YES_NO_OPTION);
                                if (resp == JOptionPane.YES_OPTION) {
                                    System.exit(0);
                                }
                                break;
                        }

                        this.stop();
                        frame.dispose();
                        SoftGym.syd.start();
                    }
                    return;
                }

            }
            SoftGym.prin.BusquedaCompleta(-1);
        } catch (Exception ex) {
            //ex.printStackTrace();
        }
    }

}
