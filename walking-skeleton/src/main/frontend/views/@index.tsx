import { ViewConfig } from '@vaadin/hilla-file-router/types.js';
import { Button, Grid, GridColumn, TextField } from '@vaadin/react-components';
import { Notification } from '@vaadin/react-components/Notification';
import { GreetingService } from 'Frontend/generated/endpoints';
import { useSignal } from '@vaadin/hilla-react-signals';
import handleError from 'Frontend/views/_ErrorHandler';
import { Group, ViewToolbar } from 'Frontend/components/ViewToolbar';
import Greeting from 'Frontend/generated/com/example/application/greeting/domain/Greeting';
import { useDataProvider } from '@vaadin/hilla-react-crud';

export const config: ViewConfig = {
  title: 'Greetings from Hilla',
  menu: {
    icon: 'vaadin:cube',
    order: 1,
    title: 'Greetings (Hilla)',
  },
};

const dateFormatter = new Intl.DateTimeFormat(undefined, {
  dateStyle: 'medium',
  timeStyle: 'medium',
});

export default function GreetingView() {
  const dataProvider = useDataProvider<Greeting>({
    list: (pageable) => GreetingService.list(pageable),
  });

  const name = useSignal('');
  const greet = async () => {
    try {
      await GreetingService.greet(name.value);
      dataProvider.refresh();
      name.value = '';
      Notification.show('Greeting added', { duration: 3000, position: 'bottom-end', theme: 'success' });
    } catch (error) {
      handleError(error);
    }
  };
  return (
    <main className="w-full h-full flex flex-col box-border gap-s p-m">
      <ViewToolbar title="Greetings from Hilla">
        <Group>
          <TextField
            placeholder="What is your name?"
            maxlength={255}
            value={name.value}
            onValueChanged={(evt) => (name.value = evt.detail.value)}
          />
          <Button onClick={greet} theme="primary">
            Greet
          </Button>
        </Group>
        <Button onClick={dataProvider.refresh}>Refresh</Button>
      </ViewToolbar>
      <Grid dataProvider={dataProvider.dataProvider}>
        <GridColumn path="greeting" />
        <GridColumn path="greetingDate" header="Date">
          {({ item }) => dateFormatter.format(new Date(item.greetingDate))}
        </GridColumn>
      </Grid>
    </main>
  );
}
