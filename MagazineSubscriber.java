import java.util.concurrent.Flow;
import java.util.logging.Logger;
import java.util.ArrayList;

public class MagazineSubscriber implements Flow.Subscriber<Integer>  {

    private static final Logger log = Logger.getLogger(MagazineSubscriber.class.getName());

    private final long sleepTime;
    private final String subscriberName;
    private Flow.Subscription subscription;
    ArrayList<Integer> consumedElements = new ArrayList<Integer>();

     /**
     * Construtor.
     * @param sleepTime tempo de descanso entre o pedido de uma nova revista.
     * @param subscriberName nome do assinante
     */
    public MagazineSubscriber(final long sleepTime, final String subscriberName) {
        this.sleepTime = sleepTime;
        this.subscriberName = subscriberName;
    }

    /**
     * O Publisher chama esse método quando um novo Assinante faz sua assinatura.
     * Normalmente, a assinatura é guardada pois pode ser usada posteriormente para enviar sinais
     * ao Publisher: solicitais mais revistas ou cancelar a assinatura.
     * Também é comum usar a assinatura para imediatamente  solicitar o primeiro item.
     * @param subscription a assinatura
     */
    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription = subscription;
        subscription.request(1);
    }

    /**
     *
     * É chamado sempre que um novo item for recebido.
     * No nosso caso, seguiremos um cenário típico que, além de processar esse item, solicitaremos um novo.
     * No entanto, entre esses, incluiremos um tempo de descanso que é configurável ao criar o assinante.
     * Dessa forma, podemos experimentar diferentes cenários e ver o que acontece quando os inscritos não
     * se comportam adequadamente.
     * Lembre-se de implementar a lógica de registrar as revistas que faltam no caso de descarte.
     * @param item a próxima revista entregue ao assinante
     */
    public void onNext(Integer item) {
        consumedElements.add(item);
        if((!(consumedElements.contains(item - 1))) && item != 1) {
            System.out.print("\nRevista perdida.Número:"  + (item - 1) + " Assinante: " + this.subscriberName + "\n");
        }
        try {
            Thread.sleep(this.sleepTime);
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        subscription.request(item);
    }

    /**
     * É chamado pelo Publisher para informar ao Assinante que algo deu errado.
     * Em nossa implementação, apenas registramos a mensagem, pois isso acontecerá quando o Publisher
     * descartar uma revista do assinante.
     * @param throwable o erro
     */
    public void onError(Throwable throwable) {
        log("Houve um erro no Publisher: " + throwable.getMessage());
    }

    /**
     * Chamado quando o Publisher não tem mais itens para enviar, então a assinatura é concluída.
     * Lembre-se de escrever no log a mensagem que indica quantas revistas o assinante recebeu.
     */
    public void onComplete() {
        log("Quantidade de revistas lidas:" + consumedElements.size());
    }

    /**
     *
     * Facilita a escrita no log junto com o nome do assinante.
     * @param logMessage mensagem a ser escrita no log
     */
    private void log(final String logMessage) {
        log.info("<=========== [" + getSubscriberName() + "] : " + logMessage);
    }

    /**
     *
     * Obtém o nome do assinante.
     * @return o nome do assinante configurado no momento de sua criação
     */
    public String getSubscriberName() {
        return subscriberName;
    }

    /**
     * Descansa pelo tempo configurado no momento da criação do assinante.
     */
    private void takeSomeRest() {
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
